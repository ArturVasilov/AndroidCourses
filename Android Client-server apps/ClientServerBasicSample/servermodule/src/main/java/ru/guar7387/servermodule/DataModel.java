package ru.guar7387.servermodule;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import ru.guar7387.servermodule.api.ConnectionManager;
import ru.guar7387.servermodule.api.RequestFactory;
import ru.guar7387.servermodule.api.SimpleRequestFactory;
import ru.guar7387.servermodule.callbacks.AnswerCallback;
import ru.guar7387.servermodule.callbacks.ArticleCommentsCallback;
import ru.guar7387.servermodule.callbacks.Callback;
import ru.guar7387.servermodule.callbacks.FullArticleTextCallback;
import ru.guar7387.servermodule.data.Article;
import ru.guar7387.servermodule.data.Comment;
import ru.guar7387.servermodule.data.User;
import ru.guar7387.servermodule.data.Vote;
import ru.guar7387.servermodule.local.database.DatabaseProvider;
import ru.guar7387.servermodule.local.database.DefaultDatabaseProvider;
import ru.guar7387.servermodule.tasksfactory.AbstractTask;
import ru.guar7387.servermodule.tasksfactory.AddCommentTask;
import ru.guar7387.servermodule.tasksfactory.LoadAllArticlesTask;
import ru.guar7387.servermodule.tasksfactory.LoadAllCommentsTask;
import ru.guar7387.servermodule.tasksfactory.LoadAllUsersTask;
import ru.guar7387.servermodule.tasksfactory.LoadAllVotesTask;
import ru.guar7387.servermodule.tasksfactory.LoadArticleCommentsTask;
import ru.guar7387.servermodule.tasksfactory.LoadArticleTextTask;

public class DataModel {

    private static final String TAG = DataModel.class.getSimpleName();

    private static final int REQUESTS_QUEUE_SIZE = 16;

    private Map<Integer, Article> mArticles;
    private Map<Integer, User> mUsers;
    private Map<Integer, Comment> mComments;
    private Map<Integer, Vote> mVotes;

    private final Context mContext;

    private RequestFactory mRequestFactory;

    private DatabaseProvider mLocalDatabaseProvider;

    private ConnectionManager mConnectionManager;

    private boolean databaseOpened = false;

    private BlockingQueue<AbstractTask> mModelRequests;

    /**
     * this should be an authorized persons, but here just for test
     */
    private final User mCurrentUser;

    public DataModel(Context context) {
        this.mContext = context;
        mCurrentUser = new User(12, "testaccount@gmail.com", "123456", "TestUser");
    }

    public User getCurrentUser() {
        return mCurrentUser;
    }

    public void loadAllInfo(final Callback callback) {
        Logger.log(TAG, "loading all the data");
        if (!hasNetConnection()) {
            Logger.log(TAG, "user has no net connection");
            loadAllFromLocalDatabase(callback);
            return;
        }

        class Counter {
            private int count = 0;
        }
        final Counter counter = new Counter();

        AbstractTask loadAllArticlesTask = new LoadAllArticlesTask(0,
                mRequestFactory.createRequest(RequestFactory.ALL_ARTICLES), mConnectionManager, null, mArticles) {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Logger.log(TAG, "all articles loaded");
                mModelRequests.remove(this);
                counter.count += 1;
                if (counter.count >= 4) {
                    callback.call();
                }
            }
        };
        AbstractTask loadAllUsersTask = new LoadAllUsersTask(1,
                mRequestFactory.createRequest(RequestFactory.ALL_USERS), mConnectionManager, null, mUsers) {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Logger.log(TAG, "all users loaded");
                mModelRequests.remove(this);
                counter.count += 1;
                if (counter.count >= 4) {
                    callback.call();
                }
            }
        };
        AbstractTask loadAllCommentsTask = new LoadAllCommentsTask(2,
                mRequestFactory.createRequest(RequestFactory.ALL_COMMENTS), mConnectionManager, null, mComments) {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Logger.log(TAG, "all comments loaded");
                mModelRequests.remove(this);
                counter.count += 1;
                if (counter.count >= 4) {
                    callback.call();
                }
            }
        };
        AbstractTask loadAllVotesTask = new LoadAllVotesTask(3,
                mRequestFactory.createRequest(RequestFactory.ALL_VOTES), mConnectionManager, null, mVotes) {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Logger.log(TAG, "all votes loaded");
                mModelRequests.remove(this);
                counter.count += 1;
                if (counter.count >= 4) {
                    callback.call();
                }
            }
        };

        Logger.log(TAG, "Adding task to queue");
        mModelRequests.add(loadAllArticlesTask);
        mModelRequests.add(loadAllUsersTask);
        mModelRequests.add(loadAllCommentsTask);
        mModelRequests.add(loadAllVotesTask);

        Logger.log(TAG, "executing loading tasks");
        loadAllArticlesTask.execute();
        loadAllUsersTask.execute();
        loadAllCommentsTask.execute();
        loadAllVotesTask.execute();
    }

    private void loadAllFromLocalDatabase(final Callback callback) {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (databaseOpened) {
                    mLocalDatabaseProvider.readAllArticles(mArticles);
                    mLocalDatabaseProvider.readAllUsers(mUsers);
                    mLocalDatabaseProvider.readAllComments(mComments);
                    mLocalDatabaseProvider.readAllVotes(mVotes);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.call();
            }
        };
        task.execute();
    }

    public void init() {
        mRequestFactory = new SimpleRequestFactory();

        mLocalDatabaseProvider = new DefaultDatabaseProvider(mContext);
        mConnectionManager = new ConnectionManager();

        mArticles = new HashMap<>();
        mUsers = new HashMap<>();
        mComments = new HashMap<>();
        mVotes = new HashMap<>();

        mUsers.put(mCurrentUser.getId(), mCurrentUser);

        mModelRequests = new ArrayBlockingQueue<>(REQUESTS_QUEUE_SIZE);

        mLocalDatabaseProvider.open(new DatabaseProvider.DatabaseOpenCallback() {
            @Override
            public void databaseOpened() {
                databaseOpened = true;
            }
        });
    }

    public List<Article> articles() {
        return new ArrayList<>(mArticles.values());
    }

    public void clearAndSave() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (!mModelRequests.isEmpty()) {
                    try {
                        long step = 10;
                        //noinspection SynchronizeOnNonFinalField
                        synchronized (mModelRequests) {
                            mModelRequests.wait(step);
                        }
                        long current = 0;
                        current += step;
                        long waitTime = 2000;
                        if (current >= waitTime) {
                            for (AsyncTask task : mModelRequests) {
                                task.cancel(true);
                            }
                            mModelRequests.clear();
                            break;
                        }
                    } catch (InterruptedException e) {
                        break;
                    }
                }

                if (databaseOpened) {
                    mLocalDatabaseProvider.updateArticles(mArticles);
                    mLocalDatabaseProvider.updateUsers(mUsers);
                    mLocalDatabaseProvider.updateComments(mComments);
                    mLocalDatabaseProvider.updateVotes(mVotes);

                    mLocalDatabaseProvider.close();
                    databaseOpened = false;
                }

                mConnectionManager.clear();

                mArticles.clear();
                mUsers.clear();
                mComments.clear();
                mVotes.clear();
            }
        };

        new Thread(runnable).start();
    }

    public void loadArticle(int id, FullArticleTextCallback textCallback, ArticleCommentsCallback commentsCallback) {
        if (!hasNetConnection()) {
            return;
        }
        AbstractTask textTask = new LoadArticleTextTask(20, mRequestFactory.createArticleRequest(id),
                mConnectionManager, textCallback) {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mModelRequests.remove(this);
            }
        };

        AbstractTask commentsTask = new LoadArticleCommentsTask(21, mRequestFactory.createArticleCommentsRequest(id),
                mConnectionManager, commentsCallback) {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mModelRequests.remove(this);
            }
        };

        mModelRequests.add(textTask);
        mModelRequests.add(commentsTask);

        textTask.execute();
        commentsTask.execute();
    }

    public void addComment(Comment comment, AnswerCallback answerCallback) {
        AbstractTask task = new AddCommentTask(31, mRequestFactory.createAddCommentRequest(comment, mCurrentUser),
                mConnectionManager, answerCallback) {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mModelRequests.remove(this);
            }
        };

        mModelRequests.add(task);
        task.execute();
    }

    public Comment getComment(int id) {
        return mComments.get(id);
    }

    public User getUser(int id) {
        return mUsers.get(id);
    }

    private boolean hasNetConnection() {
        try {
            String cs = Context.CONNECTIVITY_SERVICE;
            ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(cs);
            NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            int TRY_COUNT = 3;
            boolean value = false;
            for (int i = 0; i < TRY_COUNT; i++) {
                if (wifi != null && wifi.isConnected()) {
                    value = true;
                    break;
                }
            }
            if (value) {
                return true;
            }

            NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            for (int i = 0; i < TRY_COUNT; i++) {
                if (wifi != null && mobile.isConnected()) {
                    value = true;
                    break;
                }
            }
            return value;
        } catch (Exception e) {
            Logger.log(TAG, "exception during checking net state");
            return false;
        }
    }
}

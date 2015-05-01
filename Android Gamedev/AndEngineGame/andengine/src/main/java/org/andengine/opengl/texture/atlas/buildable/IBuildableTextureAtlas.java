package org.andengine.opengl.texture.atlas.buildable;

import org.andengine.opengl.texture.atlas.ITextureAtlas;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.atlas.source.ITextureAtlasSource;
import org.andengine.util.call.Callback;

/**
 * (c) 2012 Zynga Inc.
 *
 * @author Nicolas Gramlich <ngramlich@zynga.com>
 * @since 15:17:47 - 23.01.2012
 */
public interface IBuildableTextureAtlas<S extends ITextureAtlasSource, T extends ITextureAtlas<S>> extends ITextureAtlas<S> {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * Most likely this is not the method you'd want to be using, as the {@link org.andengine.opengl.texture.atlas.source.ITextureAtlasSource} won't get built through this {@link BuildableTextureAtlas}.
	 * @deprecated Use {@link IBuildableTextureAtlas#addTextureAtlasSource(org.andengine.opengl.texture.atlas.source.ITextureAtlasSource)} instead.
	 */
	@Deprecated
	public void addTextureAtlasSource(final S pTextureAtlasSource, final int pTextureX, final int pTextureY);

	/**
	 * Most likely this is not the method you'd want to be using, as the {@link org.andengine.opengl.texture.atlas.source.ITextureAtlasSource} won't get built through this {@link BuildableTextureAtlas}.
	 * @deprecated Use {@link IBuildableTextureAtlas#addTextureAtlasSource(org.andengine.opengl.texture.atlas.source.ITextureAtlasSource)} instead.
	 */
	@Deprecated
	public void addTextureAtlasSource(final S pTextureAtlasSource, final int pTextureX, final int pTextureY, final int pTextureAtlasSourcePadding);

	/**
	 * When all {@link org.andengine.opengl.texture.atlas.source.ITextureAtlasSource}s are added you have to call {@link #build(org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder)}.
	 *
	 * @param pTextureAtlasSource to be added.
	 * @param pCallback
	 */
	public void addTextureAtlasSource(final S pTextureAtlasSource, final Callback<S> pCallback);

	/**
	 * Removes a {@link org.andengine.opengl.texture.atlas.source.ITextureAtlasSource} before {@link #build(org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder)} is called.
	 * @param pBitmapTextureAtlasSource to be removed.
	 */
	public void removeTextureAtlasSource(final ITextureAtlasSource pTextureAtlasSource);

	/**
	 * May draw over already added {@link org.andengine.opengl.texture.atlas.source.ITextureAtlasSource}.
	 *
	 * @param pTextureAtlasBuilder the {@link org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder} to use for building the {@link org.andengine.opengl.texture.atlas.source.ITextureAtlasSource} in this {@link IBuildableTextureAtlas}.
	 * @return itself for method chaining.
	 * @throws org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException i.e. when the {@link org.andengine.opengl.texture.atlas.source.ITextureAtlasSource} didn't fit into this {@link IBuildableTextureAtlas}.
	 */
	public IBuildableTextureAtlas<S, T> build(final ITextureAtlasBuilder<S, T> pTextureAtlasBuilder) throws TextureAtlasBuilderException;
}
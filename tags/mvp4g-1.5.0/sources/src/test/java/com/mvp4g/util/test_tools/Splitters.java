package com.mvp4g.util.test_tools;

import com.mvp4g.client.Mvp4gSplitter;
import com.mvp4g.client.annotation.module.Loader;

public class Splitters {
	
	public interface SimpleSplitter extends Mvp4gSplitter{}
	
	@Loader(Loaders.Loader1.class)
	public interface SplitterWithLoader extends Mvp4gSplitter{}
	
	@Loader(Loaders.Loader2.class)
	public interface SplitterWithSameLoader1 extends Mvp4gSplitter{}
	
	@Loader(Loaders.Loader2.class)
	public interface SplitterWithSameLoader2 extends Mvp4gSplitter{}

}

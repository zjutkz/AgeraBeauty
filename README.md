#AgeraBeauties

A sample App based on [Gank.io's Api](http://gank.io/api), using [Agera](https://github.com/google/agera) as the model engine.



#Why

Recently,there are so many Apps choosing RxJava+retrofit+MVP to be the framework of itself,while I thought  [Agera](https://github.com/google/agera) may be the better one in Android platform instead of RxJava because I think the latter is so heavy and Agera can do all the things RxJava does.

So I made this App to show how to use Agera to replace RxJava in the model layer of an Android App.



#How Agera works

Agera+retrofit:

```java
BeautyApi api = BeautyService.getInstance().getNetEngine().create(BeautyApi.class);
repository = Repositories.repositoryWithInitialValue(beauty)
                .observe()
                .onUpdatesPerLoop()
                .goTo(Executors.newSingleThreadExecutor())
                .thenAttemptGetFrom(api.getUserInfo(page++))
                .orEnd(errorHandler)
                .compile();
```

AgeraBus:

```java
AgeraBus.eventRepositories().post(new LoadEvent(true,lastAction,beauties.results));
```

```java
@Override
public void onEventReceiveInMain() {
    Object event = AgeraBus.eventRepositories().get();
    if(event instanceof LoadEvent){
        if(!((LoadEvent) event).isSuccess){
            beauties.showNoDataView();
            return;
        }
        if(((LoadEvent) event).reqType == MainPresenter.REFRESH){
            beauties.stopRefresh();
        }else {
            beauties.stopLoadMore();
        }
        setBeautiesToAdapter((LoadEvent)event);
    }else if(event instanceof RouteEvent){
        RouteEvent routeEvent = (RouteEvent)event;
        if(RouterProtocol.GALLERY.equals(routeEvent.protocol)){
            jumpToGallery(routeEvent);
        }
    }
}
```

Saving a picture using Agera:

```java
public void savePic(final String data) throws ExecutionException, InterruptedException {
    lastAction = ACTION_SAVE;

    repo = Repositories.repositoryWithInitialValue(data)
            .observe()
            .onUpdatesPerLoop()
            .goLazy()
            .attemptTransform(FileUtils.savePic())
            .orEnd(errorHandler)
            .thenGetFrom(successHandler)
            .compile();
}
```

```java
public static Function<String,Result<Boolean>> savePic(){

    return Functions.functionFrom(String.class)
            .apply(FunctionProvider.fileStreamFunction)
            .thenApply(FunctionProvider.savePicFunction);
}
```

So you can see everything related with data in this App can be handled with the help of Agera.



#Thanks to

[daimajia](https://github.com/daimajia),[Gank.io](http://gank.io)'s organizer.

[retrofit-agera-call-adapter](https://github.com/drakeet/retrofit-agera-call-adapter),made by [drakeet](https://github.com/drakeet).

[retrofit](https://github.com/square/retrofit) and its gson converter,made by [square](https://github.com/square).

[mosby](https://github.com/sockeqwe/mosby),made by [sockeqwe](https://github.com/sockeqwe).

[glide](https://github.com/bumptech/glide),made by [bumptech](https://github.com/bumptech).

[android-gif-drawable](https://github.com/koral--/android-gif-drawable).made by [koral--](https://github.com/koral--).

[PhotoView](https://github.com/chrisbanes/PhotoView),made by [chrisbanes](https://github.com/chrisbanes).

[BottomSheet](https://github.com/Kennyc1012/BottomSheet),made by [Kennyc1012](https://github.com/Kennyc1012).

[roundedimageview](https://github.com/vinc3m1/RoundedImageView),made by [vinc3m1](https://github.com/vinc3m1).

[infinitecycleviewpager](https://github.com/DevLight-Mobile-Agency/InfiniteCycleViewPager),made by [Devlight](https://github.com/DevLight-Mobile-Agency).

At last,two repositories made by myself:

[AgeraBus](https://github.com/zjutkz/AgeraBus).

[PowerfulRecyclerView](https://github.com/zjutkz/PowerfulRecyclerView).

#License

```
Copyright 2016 zjutkz

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
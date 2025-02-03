package com.basebox.weatherinsights;

import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
    topLevelClass = MyDataApp.class
)
@GeneratedEntryPoint
@InstallIn(SingletonComponent.class)
public interface MyDataApp_GeneratedInjector {
  void injectMyDataApp(MyDataApp myDataApp);
}

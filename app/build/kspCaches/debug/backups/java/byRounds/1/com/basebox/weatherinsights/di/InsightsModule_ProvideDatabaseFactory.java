package com.basebox.weatherinsights.di;

import android.content.Context;
import com.basebox.weatherinsights.data.db.RoomDB;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class InsightsModule_ProvideDatabaseFactory implements Factory<RoomDB> {
  private final Provider<Context> contextProvider;

  public InsightsModule_ProvideDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public RoomDB get() {
    return provideDatabase(contextProvider.get());
  }

  public static InsightsModule_ProvideDatabaseFactory create(Provider<Context> contextProvider) {
    return new InsightsModule_ProvideDatabaseFactory(contextProvider);
  }

  public static RoomDB provideDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(InsightsModule.INSTANCE.provideDatabase(context));
  }
}

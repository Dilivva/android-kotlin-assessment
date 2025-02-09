package com.basebox.weatherinsights.di;

import com.basebox.weatherinsights.data.db.RoomDB;
import com.basebox.weatherinsights.data.db.WeatherDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
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
public final class InsightsModule_ProvideWeatherDaoFactory implements Factory<WeatherDao> {
  private final Provider<RoomDB> dbProvider;

  public InsightsModule_ProvideWeatherDaoFactory(Provider<RoomDB> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public WeatherDao get() {
    return provideWeatherDao(dbProvider.get());
  }

  public static InsightsModule_ProvideWeatherDaoFactory create(Provider<RoomDB> dbProvider) {
    return new InsightsModule_ProvideWeatherDaoFactory(dbProvider);
  }

  public static WeatherDao provideWeatherDao(RoomDB db) {
    return Preconditions.checkNotNullFromProvides(InsightsModule.INSTANCE.provideWeatherDao(db));
  }
}

package com.basebox.weatherinsights.data.repo;

import com.basebox.weatherinsights.data.db.WeatherDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class InsightRepository_Factory implements Factory<InsightRepository> {
  private final Provider<WeatherDao> daoProvider;

  public InsightRepository_Factory(Provider<WeatherDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public InsightRepository get() {
    return newInstance(daoProvider.get());
  }

  public static InsightRepository_Factory create(Provider<WeatherDao> daoProvider) {
    return new InsightRepository_Factory(daoProvider);
  }

  public static InsightRepository newInstance(WeatherDao dao) {
    return new InsightRepository(dao);
  }
}

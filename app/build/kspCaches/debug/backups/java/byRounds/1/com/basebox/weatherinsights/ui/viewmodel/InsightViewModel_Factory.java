package com.basebox.weatherinsights.ui.viewmodel;

import com.basebox.weatherinsights.data.repo.InsightRepository;
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
public final class InsightViewModel_Factory implements Factory<InsightViewModel> {
  private final Provider<InsightRepository> repositoryProvider;

  public InsightViewModel_Factory(Provider<InsightRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public InsightViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static InsightViewModel_Factory create(Provider<InsightRepository> repositoryProvider) {
    return new InsightViewModel_Factory(repositoryProvider);
  }

  public static InsightViewModel newInstance(InsightRepository repository) {
    return new InsightViewModel(repository);
  }
}

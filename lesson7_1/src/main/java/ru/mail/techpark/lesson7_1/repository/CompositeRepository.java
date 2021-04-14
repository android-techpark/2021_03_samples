package ru.mail.techpark.lesson7_1.repository;

public class CompositeRepository implements CredentialsRepository{

    CredentialsRepository mNetworkRepo;
    CredentialsRepository mLocalRepo;

    public CompositeRepository(CredentialsRepository networkRepo, CredentialsRepository localRepo) {
        mNetworkRepo = networkRepo;
        mLocalRepo = localRepo;
    }


    @Override
    public void validateCredentials(final String login, final String pass, final ValidationCallback validationCallback) {
        mLocalRepo.validateCredentials(login, pass, new ValidationCallback() {
            @Override
            public void onSuccess() {
                validationCallback.onSuccess();
            }

            @Override
            public void onError() {
                validationCallback.onError();
            }

            @Override
            public void onNotFound() {
                mNetworkRepo.validateCredentials(login, pass, validationCallback);
            }
        });

    }
}

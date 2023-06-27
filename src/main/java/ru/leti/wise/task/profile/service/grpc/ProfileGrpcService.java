package ru.leti.wise.task.profile.service.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import ru.leti.wise.task.profile.ProfileGrpc.*;
import ru.leti.wise.task.profile.ProfileServiceGrpc.ProfileServiceImplBase;
import ru.leti.wise.task.profile.logic.GetAllProfilesOperation;
import ru.leti.wise.task.profile.logic.SignInOperation;
import ru.leti.wise.task.profile.logic.SignUpOperation;

@Slf4j
@GRpcService
@RequiredArgsConstructor
public class ProfileGrpcService extends ProfileServiceImplBase {

    private final SignUpOperation signUpOperation;
    private final SignInOperation signInOperation;
    private final GetAllProfilesOperation getAllProfilesOperation;

    @Override
    public void getProfile(GetProfileRequest request, StreamObserver<GetProfileResponse> responseObserver) {
        super.getProfile(request, responseObserver);
    }

    @Override
    public void deleteProfile(DeleteProfileRequest request, StreamObserver<Empty> responseObserver) {
        super.deleteProfile(request, responseObserver);
    }

    @Override
    public void updateProfile(UpdateProfileResponse request, StreamObserver<UpdateProfileResponse> responseObserver) {
        super.updateProfile(request, responseObserver);
    }

    @Override
    public void getProfileStatistic(GetProfileStatisticRequest request, StreamObserver<GetProfileStatisticResponse> responseObserver) {
        super.getProfileStatistic(request, responseObserver);
    }

    @Override
    public void signUp(SignUpRequest request, StreamObserver<SignUpResponse> responseObserver) {
        responseObserver.onNext(signUpOperation.activate(request));
        responseObserver.onCompleted();
    }

    @Override
    public void signIn(SignInRequest request, StreamObserver<SignInResponse> responseObserver) {
        responseObserver.onNext(signInOperation.activate(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getAllProfiles(Empty request, StreamObserver<GetAllProfilesResponse> responseObserver) {
        responseObserver.onNext(getAllProfilesOperation.activate());
        responseObserver.onCompleted();
    }
}

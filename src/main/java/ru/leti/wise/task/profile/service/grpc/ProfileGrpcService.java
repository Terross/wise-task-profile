package ru.leti.wise.task.profile.service.grpc;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.lognet.springboot.grpc.recovery.GRpcExceptionHandler;
import org.lognet.springboot.grpc.recovery.GRpcExceptionScope;
import org.lognet.springboot.grpc.recovery.GRpcServiceAdvice;
import ru.leti.wise.task.profile.ProfileGrpc.*;
import ru.leti.wise.task.profile.ProfileServiceGrpc.ProfileServiceImplBase;
import ru.leti.wise.task.profile.error.BusinessException;
import ru.leti.wise.task.profile.error.GrpcErrorHandler;
import ru.leti.wise.task.profile.helper.LogInterceptor;
import ru.leti.wise.task.profile.logic.*;

import java.util.UUID;

@Slf4j
@GRpcService(interceptors = { LogInterceptor.class })
@RequiredArgsConstructor
public class ProfileGrpcService extends ProfileServiceImplBase {

    private final SignUpOperation signUpOperation;
    private final SignInOperation signInOperation;
    private final GetProfileOperation getProfileOperation;
    private final DeleteProfileOperation deleteProfileOperation;
    private final UpdateProfileOperation updateProfileOperation;
    private final GetAllProfilesOperation getAllProfilesOperation;

    @Override
    public void getProfile(GetProfileRequest request, StreamObserver<GetProfileResponse> responseObserver) {
        responseObserver.onNext(getProfileOperation.activate(UUID.fromString(request.getProfileId())));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteProfile(DeleteProfileRequest request, StreamObserver<Empty> responseObserver) {
        deleteProfileOperation.activate(UUID.fromString(request.getProfileId()));
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateProfile(UpdateProfileRequest request, StreamObserver<UpdateProfileResponse> responseObserver) {
        responseObserver.onNext(updateProfileOperation.activate(request.getProfile()));
        responseObserver.onCompleted();
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

    @GRpcServiceAdvice
    @RequiredArgsConstructor
    static class ErrorHandler {
        private final GrpcErrorHandler grpcErrorHandler;

        @GRpcExceptionHandler
        public Status handleBusinessException(BusinessException e, GRpcExceptionScope scope) {
            return grpcErrorHandler.processError(e);
        }
    }
}

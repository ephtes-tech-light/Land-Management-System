package com.land_management.user.grpc;

import com.land_management.user.service.UserService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
public class UserGrpcService extends UserGrpcServiceGrpc.UserGrpcServiceImplBase{
    private final UserService userService;
    @Override
    public void validateUser(UserValidationRequest request,
                             StreamObserver<UserValidationResponse> responseObserver) {

        UUID userId = UUID.fromString(request.getUserId());

        boolean exists = userService.isExistedById(userId);
        boolean approved = userService.isApprovedUser(userId);

        UserValidationResponse response =
                UserValidationResponse.newBuilder()
                        .setExists(exists)
                        .setApproved(approved)
                        .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    
}

package com.dmBackend.app.useCase.vehicle;

public interface DeleteVehicleUseCase {
    void execute(String userId, Long vehicleId);
}
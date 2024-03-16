package com.project.hms.utility;

import org.springframework.stereotype.Component;

@Component
public class AppMessages {

    @Component
    public class Success{
        public final String roomAdded = "Room added successfully!"
                , userUpdated = "User updated Successfully!"
                , userDeleted = "User deleted Successfully!"
                , loggedIn = "Logged in successfully!"
                , bookingAdded = "Booking created successfully!"
                , bookingUpdated = "Booking updated Successfully!"
                , channelDeleted = "Channel deleted Successfully!"
                , productAdded = "Product added successfully!"
                , productUpdated = "Product updated Successfully!"
                , productAssigned = "Product assigned Successfully!"
                , productDeleted = "Product deleted Successfully!";
    }

    @Component
    public class Error{
        public final String roomNotAdded = "Error saving Room. Room not added!"
                , userNotUpdated = "Error saving User. User not updated!"
                , userNotDeleted = "Error deleting User. User not deleted!"
                , userNameExist = "User name already exists!"
                , roomDoesNotExist = "Room doesn't exist!"
                , userCantBeDeleted = "This user can't be deleted!"
                , userIsNotSalesPerson = "Product can't be assigned. This user is not a sales person!"
                , bookingNotAdded = "Error saving Booking. Booking not added!"
                , channelNotUpdated = "Error saving Channel. Channel not updated!"
                , channelNotDeleted = "Error deleting Channel. Channel not deleted!"
                , channelExist = "Channel already exists!"
                , bookingDoesNotExist = "Booking doesn't exist!"
                , productNotAdded = "Error saving Product. Product not added!"
                , productNotUpdated = "Error saving Product. Product not updated!"
                , productNotAssigned = "Error assigning Product. Product not assigned!"
                , productNotDeleted = "Error deleting Product. Product not deleted!"
                , productExist = "Product already exists!"
                , productDoesNotExist = "Product doesn't exist!"
                , invalidCredentials = "Invalid login credentials!"
                , provideAllFields = "Provide all the fields!"
                , notAuthorized = "Authentication failed. Please re-login again"
                , errorLogging = "Unknown error occurred while logging in!"
                , permissionDenied = "Permission denied. You are not allowed to perform this task!"
                , unknownErrorOccurred = "Unknown error occurred at our end. Please try later!"
                , checkInOutTimeConflict = "Check out time must be after check in time!"
                , advanceTotalConflict = "Advance must be less than or equal to total amount!";
    }
}

package com.project.hms.utility;

import org.springframework.stereotype.Component;

@Component
public class AppMessages {

    @Component
    public class Success{
        public final String roomAdded = "Room added successfully!"
                , roomUpdated = "Room updated successfully!"

                , roomCategoryAdded = "Room Category added successfully!"
                , roomCategoryUpdated = "Room Category updated successfully!"

                , bookingAdded = "Booking added successfully!"
                , bookingUpdated = "Booking updated successfully!"
                , bookingAddOnPriceUpdated = "Booking addon prices updated successfully!"

                , addOnAdded = "AddOn added successfully!"
                , addOnUpdated = "AddOn updated successfully!"

                , loggedIn = "Logged in successfully!";
    }

    @Component
    public class Error{
        public final String roomNotAdded = "Error saving Room. Room not added!"
                , roomDoesNotExist = "Room doesn't exist!"
                , roomNotUpdated = "Error updating Room. Room not updated!"
                , roomNotAvailable = "This room is not available for the given range. Try a different room!"

                , roomCategoryNotAdded = "Error saving Room Category. Room Category not added!"
                , roomCategoryDoesNotExist = "Room Category doesn't exist!"
                , roomInventoryDoesNotExist = "Room Inventory doesn't exist!"
                , roomCategoryNotUpdated = "Error updating Room Category. Room Category not updated!"

                , bookingNotAdded = "Error saving Booking. Booking not added!"
                , bookingDoesNotExist = "Booking doesn't exist!"
                , bookingNotUpdated = "Error updating Booking. Booking not updated!"
                , bookingAddOnPriceNotUpdated = "Error updating Booking. Booking addon prices not updated!"
                , bookingNoAddon = "This booking has no addons currently!"

                , addOnNotAdded = "Error saving AddOn. AddOn not added!"
                , addOnDoesNotExist = "AddOn doesn't exist!"
                , addOnNotUpdated = "Add On Update failed!"

                , invalidCredentials = "Invalid login credentials!"
                , provideAllFields = "Provide all the fields!"
                , notAuthorized = "Authentication failed. Please re-login again"
                , errorLogging = "Unknown error occurred while logging in!"
                , permissionDenied = "Permission denied. You are not allowed to perform this task!"
                , unknownErrorOccurred = "Unknown error occurred at our end. Please try later!"
                , checkInOutTimeConflict = "Check out time must be after check in time!"
                , checkInTimeConflict = "Check in time must be after current time!"
                , advanceTotalConflict = "Advance must be less than or equal to total amount!";
    }
}

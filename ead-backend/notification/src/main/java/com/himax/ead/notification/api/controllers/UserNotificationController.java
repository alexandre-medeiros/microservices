package com.himax.ead.notification.api.controllers;

import com.himax.ead.notification.api.dtos.NotificationDto;
import com.himax.ead.notification.domain.models.NotificationModel;
import com.himax.ead.notification.domain.services.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserNotificationController {

    final NotificationService notificationService;

    public UserNotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity<Page<NotificationModel>> getAllNotificationsByUser(@PathVariable(value = "userId") UUID userId,
                                                                             @PageableDefault(page = 0, size = 10, sort = "notificationId", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.findAllNotificationsByUser(userId, pageable));
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PutMapping("/users/{userId}/notifications/{notificationId}")
    public ResponseEntity<Object> updateNotification(@PathVariable(value = "userId") UUID userId,
                                                     @PathVariable(value = "notificationId") UUID notificationId,
                                                     @RequestBody @Valid NotificationDto notificationDto) {
        Optional<NotificationModel> notificationModelOptional =
                notificationService.findByNotificationIdAndUserId(notificationId, userId);
        if (notificationModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found!");
        }
        notificationModelOptional.get().setNotificationStatus(notificationDto.getNotificationStatus());
        notificationService.saveNotification(notificationModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(notificationModelOptional.get());
    }


}

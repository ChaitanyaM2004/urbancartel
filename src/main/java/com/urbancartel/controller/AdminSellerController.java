package com.urbancartel.controller;

import com.urbancartel.dto.*;
import com.urbancartel.service.AdminSellerService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.urbancartel.dto.SellerResponse;

import java.util.List;

@RestController
@RequestMapping("/api/admin/sellers")
@RequiredArgsConstructor
public class AdminSellerController {
    private final AdminSellerService adminSellerService;
    @PostMapping("/{adminId}")
    public ResponseEntity<SellerResponse> AddSeller(@PathVariable Long adminId, @RequestBody SellerRequest req) {
        System.out.println("hello");
        return ResponseEntity.ok(adminSellerService.createSellerByAdmin(adminId, req));
    }
    @GetMapping("/{adminId}")
    public ResponseEntity<List<SellerResponse>> getAllSellerForAdmin(@PathVariable Long adminId) {
        System.out.println("hello from get");
        return ResponseEntity.ok(adminSellerService.getAllForAdmin(adminId));
    }
    @PutMapping("/{adminId}/{sellerId}")
    public ResponseEntity<SellerResponse> updateSellerByAdmin(@PathVariable Long adminId, @PathVariable Long sellerId, @RequestBody SellerRequest req) {
        System.out.print("at put");
        return ResponseEntity.ok(adminSellerService.updateSellerByAdmin(adminId, sellerId, req));
    }
    @DeleteMapping("/{adminId}/{sellerId}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long adminId, @PathVariable Long sellerId) {
        adminSellerService.deleteForAdmin(adminId, sellerId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{adminId}/{sellerId}")
    public ResponseEntity<Void> blockSeller(@PathVariable Long adminId, @PathVariable Long sellerId , @RequestBody BlockedRequest req) {
        adminSellerService.blockSellerByAdmin(adminId, sellerId , req.getReason());
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/unblock-s/{adminId}/{sellerId}")
    public ResponseEntity<Void> unblockSeller(@PathVariable Long adminId, @PathVariable Long sellerId) {
        adminSellerService.unblockSellerByAdmin(adminId, sellerId);
        return ResponseEntity.noContent().build();
    }

}

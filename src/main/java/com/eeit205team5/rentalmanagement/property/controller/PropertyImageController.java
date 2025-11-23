package com.eeit205team5.rentalmanagement.property.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eeit205team5.rentalmanagement.common.dto.response.ApiResponse;
import com.eeit205team5.rentalmanagement.property.entity.PropertyImage;
import com.eeit205team5.rentalmanagement.property.service.PropertyImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/properties/{propertyId}/images")
@RequiredArgsConstructor
public class PropertyImageController {

    private final PropertyImageService propertyImageService;

    // 查詢房源所有圖片
    @GetMapping
    public ApiResponse<List<PropertyImage>> getImages(@PathVariable Long propertyId) {
        return ApiResponse.success(propertyImageService.getImagesByPropertyId(propertyId));
    }

    // 上傳圖片（支援多張）
    @PostMapping
    public ApiResponse<List<PropertyImage>> uploadImages(
            @PathVariable Long propertyId,
            @RequestParam("files") List<MultipartFile> files) {
        return ApiResponse.success(propertyImageService.uploadImages(propertyId, files));
    }

    // 設定封面
    // @PutMapping("/{imageId}/cover")
    // public ApiResponse<Void> setCover(
    // @PathVariable Long propertyId,
    // @PathVariable Long imageId) {
    // propertyImageService.setCover(propertyId, imageId);
    // return ApiResponse.success("封面設定成功");
    // }

    // 更新排序
    // @PutMapping("/order")
    // public ApiResponse<Void> updateOrder(
    // @PathVariable Long propertyId,
    // @RequestBody List<PropertyImage> orderList) {
    // propertyImageService.updateOrder(propertyId, orderList);
    // return ApiResponse.success("排序更新成功");
    // }

    // 刪除圖片
    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable Long propertyId,
            @PathVariable Long imageId) {
        propertyImageService.deleteImage(propertyId, imageId);
        return ResponseEntity.noContent().build();
    }
}
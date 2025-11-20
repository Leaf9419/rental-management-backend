package com.eeit205team5.rentalmanagement.common.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
注意:不管ApiResponse裡的內容怎樣，HTTP狀態永遠是200
想改狀態碼要用ResponseEntity包起來，如:return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail("資料格式錯誤"));
- ResponseEntity控制HTTP層(狀態碼、Header)
- ApiResponse控制應用層(訊息、資料格式)
*/
@Getter
@Setter
@Builder // 建造者模式，可以用鏈式呼叫的方式建立物件
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private Boolean success;
    private String message;
    private Long count;
    private T data;

    // 分頁資訊
    private Integer size; // 每頁筆數
    private Integer page; // 當前頁(從0開始)
    private Integer totalPages; // 總頁數
    private Boolean hasNext; // 是否有下一頁
    private Boolean hasPrevious; // 是否有上一頁

    // 便利方法
    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<List<T>> successPage(Page<T> page, String message) {
        return ApiResponse.<List<T>>builder()
                .success(true)
                .message(message)
                .data(page.getContent())
                .count(page.getTotalElements())
                .size(page.getSize())
                .page(page.getNumber())
                .totalPages(page.getTotalPages())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build();
    }

    public static <T> ApiResponse<T> fail(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .build();
    }
}
package com.webcuoiky.softwareshop.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

//import jakarta.validation.constraints.NotEmpty;
//CRUD
public class SoftwareDTO {
    @NotEmpty(message = "Tên không được bỏ trống")
    private String name;

    @NotEmpty(message = "Mô tả sản phẩm không được bỏ trống")
    @Size(min = 10, message = "Mô tả sản phẩm tối thiểu 10 kí tự")
    @Size(max = 1000, message = "Mô tả sản phẩm tối đa 1000 kí tự")
    private String description;

    //@NotEmpty(message = "The dec price required")
    @NotNull(message = "The price required") //float nên là notnull thay vì notempty
    private Float price;

    private Float sale_price;

    @NotNull(message = "Số lượng không được bỏ trống")
    private Integer quantity;

    @NotEmpty(message = "The category required")
    private String category;

    //@NotEmpty(message = "The dec image required")
    private MultipartFile image;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public @NotEmpty(message = "Tên ko được bỏ trống") String getName() {
        return name;
    }

    public void setName(@NotEmpty() String name) {
        this.name = name;
    }

    public @NotEmpty(message = "Mô tả sản phẩm không được bỏ trống") String getDescription() {
        return description;
    }

    public void setDescription(@NotEmpty(message = "The description required") String description) {
        this.description = description;
    }

    public @NotNull(message = "The price required") Float getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = "Chưa nhập giá tiền") Float price) {
        this.price = price;
    }

    public Float getSale_price() {
        return sale_price;
    }

    public void setSale_price(Float sale_price) {
        this.sale_price = sale_price;
    }

    public @NotNull(message = "Số lượng không được bỏ trống") Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@NotNull(message = "Số lượng không được bỏ trống") Integer quantity) {
        this.quantity = quantity;
    }

    public @NotEmpty(message = "Chưa chọn danh mục") String getCategory() {
        return category;
    }

    public void setCategory(@NotEmpty(message = "The dec category required") String category) {
        this.category = category;
    }


}
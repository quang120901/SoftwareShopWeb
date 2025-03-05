package com.webcuoiky.softwareshop.controller;

import com.webcuoiky.softwareshop.model.Software;
import com.webcuoiky.softwareshop.model.SoftwareDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.webcuoiky.softwareshop.repository.SoftwareRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/")
public class CRUDController {

    @Autowired
    private SoftwareRepository repo;

    /* @RequestMapping("")
    public  String admin() {
        return "redirect:/admin/product-list";
    } */

    @GetMapping("product-list")
    public String showSoftwareList(Model model) {
        List<Software> softwareList = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("softwares", softwareList);
        return "adminPage/admin_product_list";
    }

    @GetMapping("product-list/add-product")
    public String showCreatePage(Model model) {
        SoftwareDTO softwareDTO = new SoftwareDTO();
        model.addAttribute("softwareDTO", softwareDTO);
        return "adminPage/admin_add_product";
    }
    @PostMapping("product-list/add-product")
    public String createSoftware(@Valid @ModelAttribute SoftwareDTO softwareDTO, BindingResult result)
    {
        if (result.hasErrors()) {
            return "adminPage/admin_add_product";
        }

        MultipartFile image = softwareDTO.getImage();
        Date createDate = new Date();
        String storageFileName = createDate.getTime() + "_" + image.getOriginalFilename();

        try {
            String uploadDir = "src/main/resources/static/img_software/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            System.out.println("exception"+ex.getMessage());
        }

        Software software = new Software();
        software.setName(softwareDTO.getName());
        software.setDescription(softwareDTO.getDescription());
        software.setCategory(softwareDTO.getCategory());
        software.setPrice(softwareDTO.getPrice());
        software.setQuantity(softwareDTO.getQuantity());
        software.setImage(storageFileName);

        repo.save(software);
        return "redirect:/admin/product-list";
    }



    @GetMapping("product-list/update-product")
    public String showUpdatePage(Model model,
                                 @RequestParam int id)
    {
        try{
            Software software = repo.findById(id).get();
            //model.addAttribute("softwareDTO", software);

            SoftwareDTO softwareDTO = new SoftwareDTO();
            softwareDTO.setName(software.getName());
            softwareDTO.setDescription(software.getDescription());
            softwareDTO.setCategory(software.getCategory());
            softwareDTO.setPrice(software.getPrice());
            softwareDTO.setQuantity(software.getQuantity());

            model.addAttribute("softwareDTO", softwareDTO);
        }
        catch (Exception ex){
            System.out.println("Exception" + ex.getMessage());
            return "adminPage/admin_product_list";
        }
        return "adminPage/admin_update_product";
    }

    @PostMapping("product-list/update-product")
    public String updateSoftware(@Valid @ModelAttribute SoftwareDTO softwareDTO,
                                 BindingResult result,
                                 Model model,
                                 @RequestParam int id){

        try{
            Software software = repo.findById(id).get();
            model.addAttribute("softwareDTO", softwareDTO);
            if(result.hasErrors()) {
                return "adminPage/admin_update_product";
            }

            if (!softwareDTO.getImage().isEmpty()) {
                //xoá ảnh cũ
                String uploadDir = "src/main/resources/static/img_software/";
                Path oldImagePath = Paths.get(uploadDir + software.getImage());

                try{
                    Files.delete(oldImagePath);
                }
                catch (Exception ex){
                    System.out.println("Exception" + ex.getMessage());
                }
                MultipartFile image = softwareDTO.getImage();
                Date createDate = new Date();
                String storageFileName = createDate.getTime() + "_" + image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }
                software.setImage(storageFileName);
            }
            software.setName(softwareDTO.getName());
            software.setDescription(softwareDTO.getDescription());
            software.setCategory(softwareDTO.getCategory());
            software.setPrice(softwareDTO.getPrice());
            software.setQuantity(softwareDTO.getQuantity());

            repo.save(software);

        }
        catch (Exception ex){
            System.out.println("Exception" + ex.getMessage());
        }

        return "redirect:/admin/product-list";
    }



    @GetMapping("product-list/delete")
    public String deleteProduct (
            @RequestParam int id
    ){
        try{
            Software software = repo.findById(id).get();
            repo.delete(software);
        }
        catch (Exception ex){
            System.out.println("Exception" + ex.getMessage());
        }
        return "redirect:/admin/product-list";  //chuyển hướng về admin/product-list
    }


}

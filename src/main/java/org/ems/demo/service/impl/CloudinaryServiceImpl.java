package org.ems.demo.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.ems.demo.entity.CompanyEntity;
import org.ems.demo.entity.UserEntity;
import org.ems.demo.exception.CompanyException;
import org.ems.demo.exception.UserException;
import org.ems.demo.repository.CompanyRepository;
import org.ems.demo.repository.UserRepository;
import org.ems.demo.service.CloudinaryService;
import org.ems.demo.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public CloudinaryServiceImpl(
            Cloudinary cloudinary,
            UserRepository userRepository,
            CompanyRepository companyRepository
    ) {
        this.cloudinary = cloudinary;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        //System.out.println(uploadResult.toString());
        return uploadResult.get("url").toString();  // Get the image URL
    }

    @Override
    public String uploadProfile(MultipartFile file, Long userId){
        try{
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            //System.out.println(uploadResult.toString());
            Optional<UserEntity> byId = userRepository.findById(userId);
            if(byId.isEmpty()) {
                throw new UserException("User not found");
            }
            UserEntity userEntity = byId.get().setProfileImage(uploadResult.get("url").toString());
            userRepository.save(userEntity);
            return uploadResult.get("url").toString();
        }
        catch(UserException e){
            throw new UserException("User not found");
        }
        catch(IOException e){
            throw new UserException("Image upload failed");
        }
    }

    @Override
    public String uploadLogo(MultipartFile file, Long companyId){
        try{
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            //System.out.println(uploadResult.toString());
            Optional<CompanyEntity> byId = companyRepository.findById(companyId);
            if(byId.isEmpty()){
                throw new CompanyException("Company not found");
            }
            CompanyEntity companyEntity = byId.get().setLogo(uploadResult.get("url").toString());
            companyRepository.save(companyEntity);
            return uploadResult.get("url").toString();
        }
        catch(CompanyException e){
            throw new CompanyException("Company not found");
        }
        catch (IOException e){
            throw new CompanyException("Image upload failed");
        }
    }
}


package com.app.pi.digitalbooking.service.impl;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.app.pi.digitalbooking.config.aws.S3ConfigCliente;
import com.app.pi.digitalbooking.model.dto.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3ServiceImpl {

    private final S3ConfigCliente s3Cliente;

    @Autowired
    public S3ServiceImpl(S3ConfigCliente s3Cliente) {
        this.s3Cliente = s3Cliente;
    }

    public String guardarImagen(MultipartFile imagen) throws IOException {
        String extensionImagen = StringUtils.getFilenameExtension(imagen.getOriginalFilename());
        String key = String.format("%s.%s", UUID.randomUUID(), extensionImagen);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(imagen.getContentType());

        PutObjectRequest guardarObjetoS3 =
                new PutObjectRequest(s3Cliente.getNombreBucketS3(), key, imagen.getInputStream(), metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);

        s3Cliente.s3Cliente().putObject(guardarObjetoS3);
        return key;
    }

    public Asset obtenerImagen(String key) throws IOException {

        S3Object objetoS3 = s3Cliente.s3Cliente().getObject(s3Cliente.getNombreBucketS3(), key);
        ObjectMetadata metadata = objetoS3.getObjectMetadata();

        return Asset.builder()
                .contenido(IOUtils.toByteArray(objetoS3.getObjectContent()))
                .tipoContenido(metadata.getContentType()).
                build();
    }

    public void eliminarImagen(String key) {
        s3Cliente.s3Cliente().deleteObject(s3Cliente.getNombreBucketS3() ,key);
    }

    public String obtenerUrlS3(String key) {
        return String.format(s3Cliente.getUrlAws(), s3Cliente.getNombreBucketS3(), key);
    }

}

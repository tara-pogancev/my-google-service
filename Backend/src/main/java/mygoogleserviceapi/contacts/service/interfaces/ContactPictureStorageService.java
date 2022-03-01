package mygoogleserviceapi.contacts.service.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ContactPictureStorageService {

    public String storeContactPicture(MultipartFile file, Long contactId);

    public Resource loadContactPicture(Long contactId);

    public void deleteContactPicture(Long contactId);

}

package com.flovvorkServer.flovvorkServer.Service;

import com.flovvorkServer.flovvorkServer.entity.Document;
import com.flovvorkServer.flovvorkServer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.print.Doc;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long>
{
    List<Document> findDocumentByUser(User user);

    List<Document> findDocumentByUserAndActiveIsLike(User user,int active);

    List<Document> findDocumentsByActiveAndUser(int active, User user);

    Document findByDocumentId(Long id);

}

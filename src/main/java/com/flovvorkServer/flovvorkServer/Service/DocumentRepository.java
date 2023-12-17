package com.flovvorkServer.flovvorkServer.Service;

import com.flovvorkServer.flovvorkServer.entity.Document;
import com.flovvorkServer.flovvorkServer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.print.Doc;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer>
{
    List<Document> findDocumentByUser(User user);

    List<Document> findDocumentByUserAndActiveIsLike(User user,int inactive);

}

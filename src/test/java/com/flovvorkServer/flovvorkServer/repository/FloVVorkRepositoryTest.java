package com.flovvorkServer.flovvorkServer.repository;

import com.flovvorkServer.flovvorkServer.Service.DocumentRepository;
import com.flovvorkServer.flovvorkServer.entity.Document;
import com.flovvorkServer.flovvorkServer.entity.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class FloVVorkRepositoryTest
{
    @Nested
    @DataJpaTest
    class DocumentRepositoryTest {

        @Autowired
        private DocumentRepository documentRepository;

        @Test
        void testFindDocumentByUser() {
            User user = new User();
            Document document = new Document();
            document.setUser(user);
            documentRepository.save(document);

            List<Document> foundDocuments = documentRepository.findDocumentByUser(user);

            assertEquals(1, foundDocuments.size());
            assertEquals(document.getDocument_id(), foundDocuments.get(0).getDocument_id());
        }

        @Test
        void testFindDocumentByUserAndActiveIsLike() {

        }

        @Test
        void testFindDocumentsByActiveAndUser() {

        }

        @Test
        void testFindByDocumentId() {
            Document savedDocument = documentRepository.save(new Document());

            Document foundDocument = documentRepository.findByDocumentId(savedDocument.getDocument_id());

            assertEquals(savedDocument.getDocument_id(), foundDocument.getDocument_id());
        }

    }
}

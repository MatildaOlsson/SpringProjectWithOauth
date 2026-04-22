package se.deved.SpringFileProjectFinal.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se.deved.SpringFileProjectFinal.models.Folder;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UploadFileRequest {

        private String fileName;

        private UUID folderId;

        private byte[] dataInBytes;

        public UploadFileRequest(String fileName, UUID folderId, byte[] bytes) {
            this.fileName = fileName;
            this.folderId = folderId;
            this.dataInBytes = bytes;

        }

}

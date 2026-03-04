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

        private String folderName;

        private byte[] dataInBytes;

        public UploadFileRequest(String fileName, String folderName, byte[] bytes) {
            this.fileName = fileName;
            this.folderName = folderName;
            this.dataInBytes = bytes;

        }

}

package se.deved.SpringFileProjectFinal.dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DownloadFileResponse {

    private String fileName;

    private byte[]  dataInBytes;

    public DownloadFileResponse(String fileName, byte[] dataInBytes) {
        this.fileName = fileName;
        this.dataInBytes = dataInBytes;
    }

}

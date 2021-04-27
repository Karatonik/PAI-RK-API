package pl.RK.PAIEVENTREST.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class FileDBDto {
    private String name;
    private String url;
    private String type;
    private long size;


}

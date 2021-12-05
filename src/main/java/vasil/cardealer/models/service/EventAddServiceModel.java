package vasil.cardealer.models.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class EventAddServiceModel extends BaseServiceModel{

    private String name;
    private String imageUrl;
    private String description;
    private String date;
}

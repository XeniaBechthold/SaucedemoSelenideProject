package api.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    public long id;
    public long petId;
    public int quantity;
    public String shipDate;
    public String status;
    public boolean complete;
}

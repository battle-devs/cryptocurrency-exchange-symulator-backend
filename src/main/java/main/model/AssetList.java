package main.model;

import lombok.Data;
import main.model.APIAsset;

import java.util.List;

@Data
public class AssetList {
    private List<APIAsset> result;

}

package main.services;

import main.entity.fromAPI.APIAsset;

import java.util.List;

interface IRestClient {
    List<APIAsset> getAssets();
}

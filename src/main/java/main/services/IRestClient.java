package main.services;

import main.entity.fromAPI.Result;

import java.io.IOException;

interface IRestClient {
    Result getAssets() throws IOException;
}

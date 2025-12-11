package com.example.securin_drive;

import org.springframework.stereotype.Service;

@Service
public interface  ReceipeService {
    void fetchAndSave(int start,int end);
}

package com.xoriant.logistics.controller;

import com.xoriant.logistics.dto.SuccessResponseDto;
import com.xoriant.logistics.dto.market.MarketItemRequest;
import com.xoriant.logistics.dto.market.MarketItemsDto;
import com.xoriant.logistics.dto.market.MarketSaleRequest;
import com.xoriant.logistics.service.MarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/market")
public class MarketController {

    private final MarketService marketService;

    @PostMapping("/mint")
    @PreAuthorize("hasRole('SUPPLIER') || hasRole('ADMIN') || hasRole('SUPER_ADMIN')")
    public ResponseEntity<SuccessResponseDto> mintNft(@RequestBody MarketItemRequest itemRequest, Principal principal) {
        return new ResponseEntity<>(marketService.mintNFT(itemRequest, principal.getName()), HttpStatus.OK);
    }

    @PutMapping("/buy-token")
    public ResponseEntity<SuccessResponseDto> buyNftToken(@RequestBody MarketSaleRequest saleRequest, Principal principal) {
        return new ResponseEntity<>(marketService.buyNftToken(saleRequest, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/unsold-items")
    public ResponseEntity<List<MarketItemsDto>> fetchUnsoldMarketItems(Principal principal) {
        return new ResponseEntity<>(marketService.fetchUnsoldMarketItems(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/user-nft")
    public ResponseEntity<List<MarketItemsDto>> fetchMyNft(Principal principal) {
        return new ResponseEntity<>(marketService.fetchUserNft(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/supplier-items")
    @PreAuthorize("hasRole('SUPPLIER') || hasRole('ADMIN') || hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<MarketItemsDto>> fetchItemsCreatedBySupplier(Principal principal) {
        return new ResponseEntity<>(marketService.fetchItemsCreatedBySupplier(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/nft-metadata/{tokenId}")
    public ResponseEntity<SuccessResponseDto> fetchNFTMetadata(@PathVariable BigInteger tokenId, Principal principal) {
        var response = SuccessResponseDto.builder()
                .response(marketService.fetchNFTMetadata(tokenId))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

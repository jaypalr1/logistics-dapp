//SPDX-License-Identifier: Unlicensed
pragma solidity ^0.7.0;

import "./ERC721/ERC721.sol";

contract LogisticsNFT is ERC721 {
    uint tokenId;

    event logTokenId(uint id);

    constructor() ERC721("LogisticsNFT", "LGNFT") {
        // setApprovalForAll(MPContract, true);
        tokenId = 1;
        _setBaseURI("https://gateway.pinata.cloud/ipfs/");
    }

    function mintNFT(string memory tokenUri) external returns (uint) {
        _safeMint(msg.sender, tokenId);
        _setTokenURI(tokenId, tokenUri);

        emit logTokenId(tokenId);

        tokenId++;

        return tokenId;
    }
}

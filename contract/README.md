# Triana Smart Contract 🏛️

A blockchain-based tourist identification system built on Ethereum that provides secure, verifiable digital identity management for tourists with hash-based data storage and QR code generation capabilities.

## 🏗️ Architecture Overview

The TouristID contract implements a comprehensive tourist identity management system with:

- **Hash-based Storage**: Tourist data stored as cryptographic hashes for privacy
- **Admin Controls**: Role-based access control with authorized issuers
- **QR Code Generation**: On-chain QR code string generation for easy verification
- **Time-based Validity**: Start and end date validation for tourist IDs
- **Status Management**: Active, revoked status tracking

## 📋 Contract Structure

### Core Functions

- `issueTouristId()` - Issue new tourist ID with hash verification
- `verifyTouristId()` - Verify tourist ID and return status
- `revokeTouristId()` - Revoke tourist ID (admin only)
- `verifyTouristHashes()` - Verify tourist data hashes
- `generateQRCode()` - Generate QR code string for tourist ID

## 🚀 Quick Start

### Prerequisites

- Node.js (v16 or later)
- npm or yarn
- MetaMask or compatible wallet
- Sepolia testnet ETH for deployment

### Installation

```bash
# Clone and setup
git clone <repository>
cd contract
npm install

# Setup environment
cp .env.example .env
# Edit .env with your Sepolia RPC URL and private key
```

### Testing

```bash
# Run all tests
npm test

# Run with gas reporting
npm run test:gas

# Run specific test file
npx hardhat test test/TouristID.test.js
```

### Deployment

#### Local Development

```bash
# Start local Hardhat node
npx hardhat node

# Deploy to local network (new terminal)
npx hardhat run scripts/deploy.js --network localhost
```

#### Sepolia Testnet

```bash
# Deploy to Sepolia testnet
npx hardhat run scripts/deploy.js --network sepolia
```

## 🌐 Network Configuration

### Sepolia Testnet

- **Chain ID**: 11155111
- **RPC URL**: Configure in `.env` file
- **Explorer**: https://sepolia.etherscan.io
- **Faucet**: https://sepoliafaucet.com

### Required Environment Variables

```env
SEPOLIA_URL=https://sepolia.infura.io/v3/YOUR_PROJECT_ID
PRIVATE_KEY=your_wallet_private_key_without_0x_prefix
```

## 🛠️ Development Workflow

### Contract Compilation

```bash
npx hardhat compile
```

### Testing Strategy

- Unit tests for all core functions
- Admin access control tests
- Hash verification tests
- Time-based validation tests
- QR code generation tests
- Gas optimization tests

### Deployment Artifacts

After deployment, check the `deployments/` directory for:

- Contract address and transaction details
- ABI JSON file for frontend integration
- Network-specific deployment information

## 🔐 Security Features

### Access Control

- **Admin-only** functions protected by `onlyAdmin` modifier
- **Role-based** permissions for tourist ID management
- **Input validation** for all critical operations

### Data Privacy

- **Hash-based storage** - sensitive data never stored on-chain
- **Zero-knowledge proofs** ready architecture
- **Selective disclosure** via hash verification

### Audit Recommendations

- [x] ReentrancyGuard implementation
- [x] Input validation and sanitation
- [x] Role-based access control
- [x] Event emission for transparency
- [x] Gas optimization patterns

## 📊 Contract Analytics

### Gas Costs (Estimated)

- Tourist ID Issuance: ~120,000 gas
- Tourist ID Verification: ~30,000 gas
- Tourist ID Revocation: ~45,000 gas
- Hash Verification: ~25,000 gas

### Test Coverage

- 21 comprehensive test cases
- 100% function coverage
- Edge case validation
- Security scenario testing

## 👥 Team

**Team Taarangini – Codenovate 25**

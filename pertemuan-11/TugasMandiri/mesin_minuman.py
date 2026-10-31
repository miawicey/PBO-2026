# MesinError dijadikan induk supaya semua error mesin bisa ditangkap sekaligus
# dengan satu `except MesinError`, sementara turunannya tetap bisa ditangani
# satu per satu dengan pesan yang berbeda.


class MesinError(Exception):
        pass

class ProdukTidakAdaError(MesinError):
    def __init__(self, kode):
        self.kode = kode
        super().__init__(f"Produk dengan kode {kode} tidak ada")


class StokHabisError(MesinError):
    def __init__(self, nama_produk):
        self.nama_produk = nama_produk
        super().__init__(f"Stok {nama_produk} habis")


class UangKurangError(MesinError):
    def __init__(self, kekurangan):
        self.kekurangan = kekurangan
        super().__init__(f"Uang kurang {kekurangan}")


def rupiah(angka):
    return "Rp" + f"{angka:,}".replace(",", ".")


class Produk:
    def __init__(self, kode, nama, harga, stok):
        self.kode = kode
        self.nama = nama
        self.harga = harga
        self.stok = stok


class MesinMinuman:
    def __init__(self):
        self.daftar_produk = []
        self.total_pendapatan = 0

    def tambah_produk(self, produk):
        self.daftar_produk.append(produk)

    def _cari_produk(self, kode):
        for p in self.daftar_produk:
            if p.kode == kode:
                return p
        return None

    def beli(self, kode, uang):
        produk = self._cari_produk(kode)
        if produk is None:
            raise ProdukTidakAdaError(kode)
        if produk.stok == 0:
            raise StokHabisError(produk.nama)
        if uang < produk.harga:
            raise UangKurangError(produk.harga - uang)

        produk.stok -= 1
        self.total_pendapatan += produk.harga
        return uang - produk.harga

    def cetak_stok(self):
        for p in self.daftar_produk:
            print(f"- {p.nama} ({p.kode}): {p.stok}")


if __name__ == "__main__":
    mesin = MesinMinuman()
    mesin.tambah_produk(Produk("A1", "Air Mineral", 3000, 5))
    mesin.tambah_produk(Produk("A2", "Teh Botol", 5000, 3))
    mesin.tambah_produk(Produk("A3", "Kopi Kaleng", 8000, 1))  
    mesin.tambah_produk(Produk("A4", "Jus Jeruk", 10000, 2))

    transaksi = [
        ("A1", 5000),    
        ("A3", 10000),  
        ("A3", 10000),  
        ("A2", 3000),  
        ("Z9", 5000),   
        ("A4", 10000),  
        ("A2", 5000),   
    ]

    print("=== MESIN MINUMAN FAKULTAS ===")
    for nomor, (kode, uang) in enumerate(transaksi, start=1):
        print(f"\nTransaksi {nomor}: kode {kode}, uang {rupiah(uang)}")
        try:
            kembalian = mesin.beli(kode, uang)
            print(f"  Berhasil! Kembalian {rupiah(kembalian)}")
        except ProdukTidakAdaError as e:
            print(f"  Maaf, kode {e.kode} tidak ditemukan. Cek lagi kodenya ya.")
        except StokHabisError as e:
            print(f"  Maaf, {e.nama_produk} sedang habis. Coba minuman lain.")
        except UangKurangError as e:
            print(f"  Uang kurang {rupiah(e.kekurangan)}. Tambahkan uangmu.")
        except MesinError as e:
            # Jaring pengaman untuk error mesin lain di masa depan
            print(f"  Terjadi kesalahan mesin: {e}")
        finally:
            print("  Mesin siap melayani pembeli berikutnya")

    print(f"\nTotal pendapatan: {rupiah(mesin.total_pendapatan)}")
    print("Sisa stok:")
    mesin.cetak_stok()
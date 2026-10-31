class JamDigital:
    def __init__(self):
        self.jam = 0
        self.menit = 0

    def atur(self, jam, menit):
        if jam < 0 or jam > 23:
            raise ValueError("jam harus 0 sampai 23")
        if menit < 0 or menit > 59:
            raise ValueError("menit harus 0 sampai 59")
        self.jam = jam
        self.menit = menit

    def tampilan(self):
        return f"{self.jam:02d}:{self.menit:02d}"


if __name__ == "__main__":
    jam = JamDigital()
    print("=== JAM DIGITAL ===")
    print(f"Tampilan awal: {jam.tampilan()}")

    percobaan = [(7, 30), (25, 0), (12, 75), (23, 59), (-1, 10)]
    for j, m in percobaan:
        try:
            jam.atur(j, m)
            print(f"Atur ke jam {j} menit {m} -> berhasil")
        except ValueError as e:
            print(f"Atur ke jam {j} menit {m} -> ditolak: {e}")
        finally:
            print(f"  Tampilan jam: {jam.tampilan()}")
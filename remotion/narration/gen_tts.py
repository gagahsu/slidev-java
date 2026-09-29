import asyncio
import json
import subprocess
import sys
from pathlib import Path

import edge_tts

HERE = Path(__file__).parent
SCRIPT = json.loads((HERE / "script.json").read_text(encoding="utf-8"))
OUT_DIR = HERE / "audio"
OUT_DIR.mkdir(exist_ok=True)
VOICE = "zh-TW-HsiaoChenNeural"


def ffprobe_duration(path: Path) -> float:
    r = subprocess.run(
        ["ffprobe", "-v", "error", "-show_entries", "format=duration",
         "-of", "default=noprint_wrappers=1:nokey=1", str(path)],
        capture_output=True, text=True, check=True,
    )
    return float(r.stdout.strip())


async def gen_all():
    durations = {}
    for item in SCRIPT:
        out_path = OUT_DIR / f"{item['id']}.mp3"
        communicate = edge_tts.Communicate(item["text"], VOICE, rate="+0%")
        await communicate.save(str(out_path))
        dur = ffprobe_duration(out_path)
        durations[item["id"]] = dur
        print(f"{item['id']}: {dur:.2f}s")
    (HERE / "durations.json").write_text(json.dumps(durations, ensure_ascii=False, indent=2), encoding="utf-8")


if __name__ == "__main__":
    asyncio.run(gen_all())

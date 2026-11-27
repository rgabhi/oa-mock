import json
from pathlib import Path

OUTPUT_DIR = Path(__file__).resolve().parent.parent / "output"


def save_report(city, data):
    """Saves an API response as output/<city>.json (pretty-printed), replacing any earlier file for that city."""
    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)
    (OUTPUT_DIR / ("%s.json" % city)).write_text(json.dumps(data, indent=2), encoding="utf-8")

from pprint import pprint
import sys
from pathlib import Path
import os
from typing import List

file_start = """<html>
<script src="https://cdn.jsdelivr.net/gh/google/code-prettify@master/loader/run_prettify.js"></script>
<link rel="stylesheet" href="../_static/template.css">
<link rel="stylesheet" href="../_static/mine.css">"""

post = '</code></pre>\n</div>'

file_end = '</html>'

output_file = 'code.html'

sys.argv += ['/home/riccardoob/repoIss23/BarbieriRiccardoIss23/Appl1Sprint2/application1/src/main/java/unibo/']

def get_javas(path: Path) -> List[Path]:
    files = []
    for i in os.walk(path):
        if i[2]:
            for j in i[2]:
                if j.endswith('.java'):
                    files.append(Path(i[0]) / j)
    return files
                

if len(sys.argv) != 2:
    print('Usage: python3 create_code.py "/path/to/code/folder')
    exit(-1)
path = Path(sys.argv[1])
if not path.exists():
    print(f'Path {path} does not exists')
    exit(-2)

files = get_javas(path)

with open(output_file, 'w+') as foutput:
    foutput.write(file_start)
    for i in files:
        with open(i, 'r') as finput:
            lines = finput.readlines()
            class_name = i.name[:-5]
            pre = f'{class_name}\n<div id="{class_name}" class="highlight">\n<pre><code class="prettyprint">'
            foutput.write(pre)
            for j in lines:
                foutput.write(j)
            foutput.write(post)
            foutput.write('')
    foutput.write(file_end)

    
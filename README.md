# iconv-super
Convert encoding of given files from one encoding to another.


## Example

Convert one file or files in directory from a given encoding to another.

```bash
./iconv-super -f big5 -t utf-8 -s math -d math-converted
```
Convert big5 to utf-8 from directory math to math-converted.It will auto-check file encoding.
If you want to copy non-big5 files in directory math ,add `-c`. 

```bash
./iconv-super -f big5 -t utf-8 -s math -d math-converted -c
```

If you want to see the log,add `-v`. 

```bash
./iconv-super -f big5 -t utf-8 -s math -d math-converted -v
```

If you want to match specific files,add `-r <regex>` with regex argv.

```bash
./iconv-super -f big5 -t utf-8 -s math -d math-converted -r .html
```
Convert .html files.
If you do not know which encoding the source files are,remove`-f`,it will anto-detect,

```bash
./iconv-super -t utf-8 -s math -d math-converted
```

Do with pipe : 
```bash
cat file |./iconv-super -f big5 -t utf-8 
```
Anto-detect with pipe:

```bash
cat file |./iconv-super  -t utf-8 
```


## Help

```bash
Usage: ./iconv-super [OPTIONS] 
     -h print this help and exit.
     -v view.
     -c copy.
     -n no convert.
     -f from encode.
     -t to encode.
     -r regex to match files
     -s from dir.
     -d to dir.
```

## License 

```
Copyright 2016-2017 dogHere

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

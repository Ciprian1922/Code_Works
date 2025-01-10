import os
import requests
import threading
from collections import Counter
from tqdm import tqdm
from urllib.parse import urlparse

#progress variables
progress = 0
progress_lock =threading.Lock()

#URLs
URLS_FILE ="urls.txt"
#output statistics
STATISTICS_FILE = "statistics.txt"

TARGET_LETTERS =set("PYTHON") #required letters

def download_file(url,filename):
    response= requests.get(url)
    with open(filename,"w",encoding="utf-8") as f:
        f.write(response.text)

#file name from URLS
def extract_filename_from_url(url):
    parsed_url =urlparse(url)
    return os.path.basename(parsed_url.path)

#analyze function
def analyze_file(filename):
    with open(filename, "r",encoding="utf-8") as f:
        text= f.read()

    words= text.split()
    word_count =len(words)

    #words starting with P Y T H O N
    filtered_words= [word for word in words if word[:1].upper() in TARGET_LETTERS]
    letter_counts =Counter(word[:1].upper() for word in filtered_words)

    #total nr of letters
    letter_total= sum(len(word) for word in words)

    return word_count,letter_counts,letter_total

#progress bar update
def update_progress():
    global progress
    with progress_lock:
        progress += 1
        pbar.update(1)

#worker function
def worker(url,idx,results):
    filename =extract_filename_from_url(url)
    download_file(url, filename)
    stats= analyze_file(filename)
    results[idx] =(filename, *stats)
    update_progress()



def main():
    #read URLs
    if not os.path.exists(URLS_FILE):
        print(f"Error: {URLS_FILE} not found!")
        return

    with open(URLS_FILE,"r") as f:
        urls = [line.strip() for line in f if line.strip()]

    threads = []
    results = [None] * len(urls)

    global pbar
    pbar = tqdm(total=len(urls),desc="Downloading and analyzing files")

    for idx, url in enumerate(urls):
        t = threading.Thread(target=worker,args=(url,idx,results))
        threads.append(t)
        t.start()

    for t in threads:
        t.join()

    pbar.close()

    #write results to stats file
    with open(STATISTICS_FILE,"w") as f:
        f.write("Nr. File name #Words #P words #Y words #T words #H words #O words #N words\n")
        f.write("-" * 90 + "\n")

        for idx, result in enumerate(results):
            filename, word_count, letter_counts, letter_total = result
            counts = [letter_counts.get(letter, 0) for letter in "PYTHON"]
            f.write(f"{idx + 1}. {filename} {word_count} {' '.join(map(str, counts))}\n")

    print(f"Statistics saved to {STATISTICS_FILE}")

if __name__ == "__main__":
    main()
year=$1
date=$2
prefix="https://discogs-data.s3-us-west-2.amazonaws.com/data/$year/discogs_$date"


folder="data_$date"

mkdir $folder

artists="${prefix}_artists.xml.gz"
labels="${prefix}_labels.xml.gz"
masters="${prefix}_masters.xml.gz"
releases="${prefix}_releases.xml.gz"

echo $artists

wget -P $folder $artists
wget -P $folder $labels
wget -P $folder $masters
wget -P $folder $releases

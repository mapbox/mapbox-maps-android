import glob


def add_content(file_name):
    with open(file_name) as f:
        data = f.read()
    return data


result = ""
files = glob.glob('*/build/dependencyUpdates/report.txt')
for file_name in files:
    result += add_content(file_name)

with open('merged_report.txt', 'w') as f:
    f.write(result)

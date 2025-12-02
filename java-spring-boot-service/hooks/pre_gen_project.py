import sys
import random


if __name__ == '__main__':

    slug = '{{ cookiecutter.project_slug }}'

    if ' ' in slug:
        print('ERROR: %s is used as a directory, try dashes or underscores instead of spaces.' % slug)
        # exits with status 1 to indicate failure
        sys.exit(1)

    sys.exit(0)

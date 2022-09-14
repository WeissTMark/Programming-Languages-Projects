from weiss_mark.Thing import Thing


class Gondola:
    def __init__(self):
        self.time = 0.0
        self.th = Thing()

    def stepTime(self):
        self.time = self.time + 0.5
        self.th.stepTime()

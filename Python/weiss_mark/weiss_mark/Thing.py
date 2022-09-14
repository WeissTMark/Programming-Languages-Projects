from weiss_mark.Stretch import Stretch
from weiss_mark.car import Car
from weiss_mark.station import Station


class Thing:
    def __init__(self, rev=False):
        self.index = 0
        self.start = Station("Start")
        self.end = Station("End")
        self.A = Station("Station A")
        self.B = Station("Station B")
        self.sa = Stretch("sa")
        self.ab = Stretch("ab")
        self.bc = Stretch("bc")
        self.stations = [self.start, self.A, self.B, self.end]
        self.stretchs = [self.sa, self.ab, self.bc]
        self.track = [self.start, self.sa, self.A, self.ab, self.B, self.bc, self.end]

    def reset(self):
        for stop in self.track:
            stop.currCar = None
            stop.waitList = []
            stop.peopleOn = 0
            stop.peopleOff = 0

    def addCar(self, behavior):
        # GRADING: SET_BEHAVIOR
        # Behavior input is passed from the main function all the way here
        # Here it is used in the constructor for a new car, setting the behavior
        # as the car is initialized.
        cart = Car(behavior, self.stations[0])
        if self.stations[0].currCar:
            self.stations[0].waitList.append(cart)
        else:
            cart.loadUnload(self.stations[0].peopleOn, self.stations[0].peopleOff)
            self.stations[0].setCar(cart)

    def stepTime(self):
        # GRADING: LOOP_ALL
        for stop in self.track:
            car = stop.currCar
            if car:
                if car.direction == 1:
                    stop.leftCar = car
                    stop.currCar = None
                    moveOn = False
                else:
                    # GRADING: USE_BEHAVIOR
                    # This function is hit twice in this loop,
                    # once for the right side, and once for the left
                    moveOn = car.useBehavior()
                if moveOn:
                    nex = self.__next__(stop, car)
                    if car.direction == 1:
                        nex.leftList.append(car)
                    else:
                        nex.waitList.append(car)
                    stop.currCar = None
            carl = stop.leftCar
            if carl:
                if carl.direction == 0:
                    stop.currCar = carl
                    stop.leftCar = None
                    moveOn = False
                else:
                    moveOn = carl.useBehavior()
                if moveOn:
                    nex = self.__next__(stop, carl)
                    if carl.direction == 0:
                        nex.waitList.append(carl)
                    else:
                        nex.leftList.append(carl)
                    stop.leftCar = None

        for stop in self.track:
            if stop.currCar is None and len(stop.waitList) > 0:
                stop.currCar = stop.waitList[0]
                stop.currCar.setLocation(stop)
                stop.currCar.loadUnload(stop.peopleOn, stop.peopleOff)
                stop.waitList.remove(stop.currCar)
            if stop.leftCar is None and len(stop.leftList) > 0:
                stop.leftCar = stop.leftList[0]
                stop.leftCar.setLocation(stop)
                stop.leftCar.loadUnload(stop.peopleOn, stop.peopleOff)
                stop.leftList.remove(stop.leftCar)

    # GRADING: INTER_ALL
    def __iter__(self):
        self.index = 0
        return self.track[self.index]

    def __next__(self, curr, car):
        for stop in range(len(self.track)):
            if curr == self.track[stop]:
                self.index = stop
        if car.direction == 0:
            self.index += 1
        else:
            self.index -= 1

        if self.index >= len(self.track):
            self.index = -2
            car.direction = 1
        elif self.index < 0:
            self.index = 1
            car.direction = 0
        return self.track[self.index]

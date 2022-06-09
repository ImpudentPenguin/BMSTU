import cv2 as cv
import matplotlib.pyplot as plt

def main():
    img1 = cv.imread('home.jpg', cv.IMREAD_GRAYSCALE)
    img2 = cv.imread('home2.JPG', cv.IMREAD_GRAYSCALE)

    sift = cv.SIFT_create()

    kp1, des1 = sift.detectAndCompute(img1, None)
    kp2, des2 = sift.detectAndCompute(img2, None)

    bf = cv.BFMatcher()
    matches = bf.knnMatch(des1, des2, k=2)

    good = []

    for m, n in matches:
        if m.distance < 0.75 * n.distance:
            good.append([m])
        if len(good) == 10:
            break

    img3 = cv.drawMatchesKnn(img1, kp1, img2, kp2, good, None, flags=cv.DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS)

    cv.imwrite('result.jpg', img3)
    plt.imshow(img3), plt.show()


if __name__ == '__main__':
    main()

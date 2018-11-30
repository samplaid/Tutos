import { trigger,style, animate, transition } from '@angular/animations';

export const simulationAnimation = trigger(
    'enterAnimation', [
      transition(':enter', [
        style({opacity: 0}),
        animate('1000ms', style({opacity: 1}))
      ])
    ]
  );

export const paymentAnimation = trigger(
  'addDeleteAnimation', [
    transition(':enter', [
      style({opacity: 0}),
      animate('350ms', style({opacity: 1}))
    ]),
    transition(':leave', [
      style({opacity: 1}),
      animate('350ms', style({opacity: 0}))
    ])
  ]
);

export const statusAnimation = trigger(
  'statusAnimation', [
    transition(':enter', [
      style({opacity: 0}),
      animate('350ms', style({opacity: 1}))
    ])
  ]
);
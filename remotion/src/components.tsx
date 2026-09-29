import React from 'react';
import {COLORS, FONT} from './theme';

export const Box: React.FC<{
  x: number;
  y: number;
  w?: number;
  h?: number;
  label: string;
  sub?: string;
  fill?: string;
  stroke?: string;
  textColor?: string;
  fontSize?: number;
  opacity?: number;
  scale?: number;
  dashed?: boolean;
}> = ({
  x,
  y,
  w = 120,
  h = 96,
  label,
  sub,
  fill = COLORS.accentLight,
  stroke = COLORS.primary,
  textColor = COLORS.primary,
  fontSize = 38,
  opacity = 1,
  scale = 1,
  dashed = false,
}) => (
  <div
    style={{
      position: 'absolute',
      left: x - w / 2,
      top: y - h / 2,
      width: w,
      height: h,
      opacity,
      transform: `scale(${scale})`,
      transformOrigin: 'center center',
    }}
  >
    <div
      style={{
        width: '100%',
        height: '100%',
        borderRadius: 14,
        background: fill,
        border: `3px ${dashed ? 'dashed' : 'solid'} ${stroke}`,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        fontSize,
        fontWeight: 700,
        color: textColor,
        fontFamily: FONT,
        boxShadow: '0 4px 10px rgba(26,92,92,0.12)',
      }}
    >
      {label}
    </div>
    {sub ? (
      <div
        style={{
          textAlign: 'center',
          marginTop: 8,
          fontSize: 22,
          color: COLORS.textMuted,
          fontFamily: FONT,
        }}
      >
        {sub}
      </div>
    ) : null}
  </div>
);

export const Arrow: React.FC<{
  x1: number;
  y1: number;
  x2: number;
  y2: number;
  curve?: number;
  color?: string;
  opacity?: number;
  strokeWidth?: number;
  progress?: number;
  dashed?: boolean;
}> = ({
  x1,
  y1,
  x2,
  y2,
  curve = 0,
  color = COLORS.primary,
  opacity = 1,
  strokeWidth = 4,
  progress = 1,
  dashed = false,
}) => {
  const mx = (x1 + x2) / 2;
  const my = (y1 + y2) / 2 - curve;
  const path = curve === 0 ? `M ${x1} ${y1} L ${x2} ${y2}` : `M ${x1} ${y1} Q ${mx} ${my} ${x2} ${y2}`;
  const markerId = `arrowhead-${color.replace('#', '')}`;
  return (
    <svg
      style={{position: 'absolute', left: 0, top: 0, width: '100%', height: '100%', pointerEvents: 'none'}}
    >
      <defs>
        <marker id={markerId} markerWidth="10" markerHeight="10" refX="7" refY="3" orient="auto">
          <path d="M0,0 L0,6 L9,3 z" fill={color} />
        </marker>
      </defs>
      <path
        d={path}
        fill="none"
        stroke={color}
        strokeWidth={strokeWidth}
        strokeDasharray={dashed ? '10 8' : 2000}
        strokeDashoffset={dashed ? 0 : 2000 * (1 - progress)}
        opacity={opacity}
        markerEnd={`url(#${markerId})`}
      />
    </svg>
  );
};

export const SceneTitle: React.FC<{title: string; subtitle?: string; opacity?: number; color?: string}> = ({
  title,
  subtitle,
  opacity = 1,
  color = COLORS.primary,
}) => (
  <div style={{position: 'absolute', top: 70, left: 0, right: 0, textAlign: 'center', opacity}}>
    <div style={{fontSize: 52, fontWeight: 800, color, fontFamily: FONT}}>{title}</div>
    {subtitle ? (
      <div style={{fontSize: 28, color: COLORS.textMuted, marginTop: 12, fontFamily: FONT}}>{subtitle}</div>
    ) : null}
  </div>
);

export const Caption: React.FC<{text: string; opacity?: number; color?: string}> = ({
  text,
  opacity = 1,
  color = COLORS.highlight,
}) => (
  <div
    style={{
      position: 'absolute',
      bottom: 90,
      left: 0,
      right: 0,
      textAlign: 'center',
      fontSize: 32,
      fontWeight: 700,
      color,
      opacity,
      fontFamily: FONT,
    }}
  >
    {text}
  </div>
);
